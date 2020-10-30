const path = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin');

const config = {
    entry: {
        'a': './src/pages/a.js',
        'b': './src/pages/b.js',
        'c': './src/pages/c.js'
    },
    output: {
        path: path.resolve(__dirname, '..', 'resources/public/static'),
        filename: 'js/[name].js',
    },
    devServer: {
        contentBase: path.resolve(__dirname, '..', 'resources/public/static/html'),
        compress: true,
        port: 3000,
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
    plugins: [
        new HtmlWebpackPlugin({
            template: 'template.ejs',
            inject: true,
            chunks: ['a'],
            filename: path.resolve(__dirname, '..', 'resources/public/static/html/a.html')
        }),
        new HtmlWebpackPlugin({
            template: 'template.ejs',
            inject: true,
            chunks: ['b'],
            filename: path.resolve(__dirname, '..', 'resources/public/static/html/b.html')
        }),
        new HtmlWebpackPlugin({
            template: 'template.ejs',
            inject: true,
            chunks: ['c'],
            filename: path.resolve(__dirname, '..', 'resources/public/static/html/c.html')
        }),
    ]
}

module.exports = config