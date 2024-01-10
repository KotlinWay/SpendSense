'use strict';

/**
 * spend-event controller
 */

const { createCoreController } = require('@strapi/strapi').factories;

module.exports = createCoreController('api::spend-event.spend-event', 
({ strapi }) => ({
 
    //default
    async find(ctx) {
       
        return await strapi.entityService.findMany('api::spend-event.spend-event', { 
            filters: { 
                userId: ctx.state.user.id
            }
        })
    },

    //custom
    async sync(ctx) {
        return await strapi.service('api::spend-event.spend-event').sync(ctx);
    }
})
);

