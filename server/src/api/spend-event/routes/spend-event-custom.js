'use strict';

module.exports = { 
    routes: [
        { 
            method: 'POST',
            path: '/spend-events/sync',
            handler: 'spend-event.sync'
        }
    ]
};