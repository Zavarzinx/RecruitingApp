// vue.config.js
module.exports = {
    // proxy all webpack dev-server requests starting with /api
    // to our Spring Boot backend (localhost:8088) using http-proxy-middleware
    // see https://cli.vuejs.org/config/#devserver-proxy
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8088',
                secure: false,
                changeOrigin: true
            },

        }
    }
    }
    // Change build paths to make them Maven compatible
    // see https://cli.vuejs.org/config/