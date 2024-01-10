'use strict';

/**
 * spend-event service
 */

const { createCoreService } = require('@strapi/strapi').factories;

module.exports = createCoreService('api::spend-event.spend-event', ({ strapi }) =>  ({

    //custom
    async sync(ctx) {
        const user = ctx.state.user
        const { body } = ctx.request
        const type = 'api::spend-event.spend-event'

        const eventsFromDb = await strapi.db.query(type)
            .findMany({
                filters: {
                    userId: user.id
                }
            })

        const dictEventsFromDb = Object.fromEntries(eventsFromDb.map(
            c => [c.idLocal, c]
        ))

        const eventsFromClient = body.filter( event =>
            dictEventsFromDb[event.idLocal] ?
            dictEventsFromDb[event.idLocal].updatedAtLocal < event.updatedAtLocal : true
        ).map( event => {
            event.userId = user.id
            return event
        })

        const eventsToCreate = eventsFromClient.filter ( event =>
            !dictEventsFromDb[event.idLocal]
        )

        const eventsToUpdate =  eventsFromClient.filter ( event =>
            dictEventsFromDb[event.idLocal]
        )

        if(eventsToCreate.length > 0){
            await strapi.db.query(type).createMany({ data: eventsToCreate })
        }

        if(eventsToUpdate.length > 0){
            await strapi.db.query(type).deleteMany({
                where: {
                    idLocal: {
                        $in: eventsToUpdate.map(c => c.idLocal)
                    }
                }
            })
            await strapi.db.query(type).createMany({
                data : eventsToUpdate
            })
        }

        const dictEventsFromClient = Object.fromEntries(body.map(c => [c.idLocal, c]))
        return eventsFromDb
            .filter(event => dictEventsFromClient[event.idLocal] ?
                dictEventsFromClient[event.idLocal].updatedAtLocal < event.updatedAtLocal : true)

    },
  }));

