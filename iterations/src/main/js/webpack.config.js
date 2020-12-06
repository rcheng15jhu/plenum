const path = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin');
const fs = require('fs')
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const HtmlWebpackHarddiskPlugin = require('html-webpack-harddisk-plugin');

let pages = fs.readdirSync('src/pages').map(i => i.replace('.js', ''))

let entryGenerator = {}
pages.forEach(page => entryGenerator[page] = './src/pages/' + page + '.js')

const config = {
    entry: entryGenerator,
    output: {
        path: path.resolve(__dirname, '..', 'resources/public/static'),
        filename: 'js/[name].js',
    },
    devServer: {
        contentBase: path.resolve(__dirname, '..', 'resources/public/static/html'),
        compress: true,
        port: 3000,
        proxy: [
            {
                context: ['/api'],
                target: 'http://localhost:7000',
            },
            {
                context: ['/'],
                target: 'http://localhost:7000',
                bypass: function (req, res, proxyOptions) {
                    console.log('url: ' + req.url)
                    if (/api|.(jpg|png)/.test(req.url)) {
                    //if (req.url.indexOf('api') !== -1 || req.url.indexOf('jpg') !== -1 || req.url.indexOf('png') !== -1) {
                        return null;
                    }
                    else if (!/^([^.#?]*\.[^\/]*\/)*[^.#?]*\.[^\/]*($|#|\?)/.test(req.url)) {
                    //else if (req.url.indexOf('html') === -1 && req.url.indexOf('js') === -1 && req.url.indexOf('css') === -1) {
                        console.log('Skipping proxy for browser request.');
                        if (req.url === '/') {
                            console.log('was root!')
                            return '/';
                        }
                        console.log("Was a redirect request!")
                        let isParam = req.url.indexOf('?')
                        let newUrlAttempt = isParam !== -1
                            ? req.url.substring(req.url.lastIndexOf('/'), isParam) + '.html' + req.url.substring(isParam)
                            : req.url.substring(req.url.lastIndexOf('/')) + '.html';
                        console.log(newUrlAttempt);
                        return newUrlAttempt;
                    }
                    else {
                        console.log('Was an html/js/css file!')
                        let jsIndex = req.url.lastIndexOf('/static/js/')
                        if (jsIndex !== -1) {
                            return req.url.substring(jsIndex + 7);
                        }
                        return req.url.substring(req.url.lastIndexOf('/'));
                    }
                }
            }
        ]
    },
    optimization: {
        splitChunks: {
            cacheGroups: {
                vendor: {
                    name: "node_vendors",
                    test: /[\\/]node_modules[\\/]/,
                    chunks: "all",
                    maxSize: 1000 * 150
                },
                common: {
                    test: /[\\/]src[\\/]components[\\/]/,
                    chunks: "all",
                    minSize: 0,
                }
            }
        }
    },
    devtool: 'source-map',
    module: {
        rules: [
            {
                test: /\.js$/,
                loader: 'babel-loader',
                options: {
                    presets: ['@babel/preset-env', '@babel/preset-react'],
                },
                exclude: /node_modules/
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader'],
            },
            {
                test: /\.(png|jpe?g|gif)$/i,
                use: [
                  {
                    loader: 'file-loader',
                  },
                ],                       
            }
        ],
    },
    plugins:
        pages.map(page =>
            new HtmlWebpackPlugin({
                title: `${page}`,
                template: 'template.ejs',
                inject: true,
                chunks: [`${page}`, 'node_vendors'],
                publicPath: 'static',
                filename: path.resolve(__dirname, '..', `resources/public/static/html/${page}.html`),
                alwaysWriteToDisk: true
            })
        )
            .concat(new HtmlWebpackHarddiskPlugin())
};

module.exports = config