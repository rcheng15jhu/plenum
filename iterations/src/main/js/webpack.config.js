const path = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin');
const fs = require('fs')

module.exports = (env, options) => {
    let pages = fs.readdirSync('src/pages').map(i => i.replace('.js', ''))

    let entryGenerator = {}
    pages.forEach(page => entryGenerator[page] = './src/pages/' + page + '.js')

    let pageGenerator = []

    if (options.mode === 'development') {
        pageGenerator = pages.map(page =>
            new HtmlWebpackPlugin({
                title: `${page}`,
                template: 'template.ejs',
                inject: true,
                chunks: [`${page}`],
                filename: path.resolve(__dirname, '..', `resources/public/static/html/${page}.html`)
            })
        )
    }
    else {
        pageGenerator = pages.map(page =>
            new HtmlWebpackPlugin({
                title: `${page}`,
                template: 'template.ejs',
                inject: true,
                chunks: [`${page}`],
                publicPath: 'static',
                filename: path.resolve(__dirname, '..', `resources/public/static/html/${page}.html`)
            })
        )
    }
    return {
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
                    bypass: function(req, res, proxyOptions) {
                        console.log('url: ' + req.url)
                        if (req.url.indexOf('api') !== -1) {
                            return null;
                        }
                        else if (req.url.indexOf('html') === -1 && req.url.indexOf('js') === -1 && req.url.indexOf('css') === -1) {
                            console.log('Skipping proxy for browser request.');
                            if(req.url === '/') {
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
                            if(jsIndex !== -1) {
                                return req.url.substring(jsIndex + 7);
                            }
                            return req.url.substring(req.url.lastIndexOf('/'));
                        }
                    }
                }
            ]
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
                },
                {
                    test: /\.css$/,
                    use: ['style-loader', 'css-loader'],
                },
            ],
        },
        plugins: pageGenerator
    }
}
