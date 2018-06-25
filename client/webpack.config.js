let HtmlWebpackPlugin = require('html-webpack-plugin');
let CompressionPlugin = require('compression-webpack-plugin');
let UglifyJSPlugin = require('uglifyjs-webpack-plugin');
let path = require('path');
let webpack = require('webpack');
const BASE = path.join(__dirname, './src/main/javascript/');
const DIST = path.join(__dirname, 'dist/');
const NODE_MODULES = path.resolve(__dirname, 'node_modules');

module.exports = function(){

    return {
        entry: {
            // vendor: path.join(BASE, 'vendor.js'),
            app: [path.join(BASE, 'vendor.js'), path.join(BASE, 'index.js')]
        },
        output: {
            filename: '[name].js',
            path: DIST
        },
        module: rules(),
        devtool: "source-map",
        plugins: plugins()
    }
};

function rules(){
    "use strict";
    let rules = {
        rules: [
            {test: /\.html$/, use: ['html-loader']},
            {
                test: /\.js$/,
                exclude: NODE_MODULES,
                use: [
                    'babel-loader'
                ]
            },


        ],
    };

    if(process.env.NODE_ENV == "test"){
        rules.rules.push({test: /\.(sass|scss)$/, use: ['raw-loader', 'sass-loader']});
    }
    else {
        rules.rules.push({test: /\.(sass|scss)$/, use: ['style-loader', 'css-loader', 'sass-loader']});
    }

    rules.rules.push(
        {test: /\.css$/, use: ['style-loader', 'css-loader']},
        {test: /\.png$/, use: ['url-loader?limit=10000']},
        {test: /\.jpg$/, use: ['file-loader']},
        {
            test: /\.(woff|woff2)(\?v=\d+\.\d+\.\d+)?$/,
            use: ['url-loader?limit=10000&mimetype=application/font-woff']
        },
        {test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/, use: ['url-loader?limit=10000&mimetype=application/octet-stream']},
        {test: /\.eot(\?v=\d+\.\d+\.\d+)?$/, use: ['file-loader']},
        {test: /\.svg(\?v=\d+\.\d+\.\d+)?$/, use: ['url-loader?limit=10000&mimetype=image/svg+xml']}
    );

    return rules;
}

function plugins() {
    let chunkPlugin = new webpack.optimize.CommonsChunkPlugin({
        name: 'vendor',
        filename: 'common.js'
    });

    let plugins = [
        new HtmlWebpackPlugin({
            template: path.join(BASE, 'index.html'),
            cache: true
        }),
        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery',
            jquery: 'jquery',
            'window.jQuery': 'jquery',
            moment: 'moment'
        }),
        new CompressionPlugin({
            asset: "[path].gz[query]"
        })
    ];
    if(process.env.NODE_ENV != "test"){
        plugins.unshift(chunkPlugin);
        plugins.unshift(new UglifyJSPlugin());
    }
    return plugins;
}
