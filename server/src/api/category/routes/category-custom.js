'use strict';

module.exports = { 
    routes: [
        { 
            method: 'POST',
            path: '/categories/sync',
            handler: 'category.sync'
        }
    ]
};