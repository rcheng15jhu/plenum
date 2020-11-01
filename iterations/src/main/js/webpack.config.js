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
            proxy: {
                '/api': {
                    target: 'http://localhost:7000',
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
