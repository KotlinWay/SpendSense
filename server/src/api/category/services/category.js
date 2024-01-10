'use strict';

/**
 * category service
 */

const { createCoreService } = require('@strapi/strapi').factories;

module.exports = createCoreService('api::category.category', ({ strapi }) =>  ({

    //custom
    async sync(ctx) {
        // Получает пользователя из контекста запроса
        // Извлекает тело запроса (ctx.request.body), которое содержит категории, отправленные клиентом.
        const user = ctx.state.user
        const { body } = ctx.request
        const type = 'api::category.category'

        // Выполняет запрос к базе данных Strapi для получения категорий, связанных с пользователем (userId: user.id).
        const categoriesFromDb = await strapi.db.query(type)
            .findMany({
                filters: {
                    userId: user.id
                }
            })

        // Преобразует массив категорий, которые есть на сервере, в словарь, где ключи - это UUID категорий.
        const dictCategoriesFromDb = Object.fromEntries(categoriesFromDb.map(
            c => [c.idLocal, c]
        ))

        // Фильтрует категории из тела запроса, оставляя те, которые либо отсутствуют на сервере, либо имеют более позднюю дату обновления.
        // Добавляет userId к каждой категории перед сохранением в базу данных.
        const categoriesFromClient = body.filter( category =>
            dictCategoriesFromDb[category.idLocal] ?
            dictCategoriesFromDb[category.idLocal].updatedAtLocal < category.updatedAtLocal : true
        ).map(category => {
            category.userId = user.id
            return category
        })

        // Фильтрует категории, которые нужно создать (те, которых ещё нет сервере).
        const categoriesToCreate = categoriesFromClient.filter (category =>
            !dictCategoriesFromDb[category.idLocal]
        )

        // Выделяет категории, которые нужно обновить (те, которые уже есть на сервере).
        const categoriesToUpdate =  categoriesFromClient.filter (category =>
            dictCategoriesFromDb[category.idLocal]
        )

        // Если есть категории для создания, то сохраняет их в бд.
        if(categoriesToCreate.length > 0){
            await strapi.db.query(type).createMany({ data: categoriesToCreate })
        }

        // Если есть категории для обновления, сначала удаляет их текущие записи из базы данных, а затем создает их заново с новыми данными.
        if(categoriesToUpdate.length > 0){
            //удалить все категории с таким idLocal
            await strapi.db.query(type).deleteMany({
                where: {
                    idLocal: {
                        $in: categoriesToUpdate.map(c => c.idLocal)
                    }
                }
            })
            //вставить новые данные
            await strapi.db.query(type).createMany({
                data : categoriesToUpdate
            })
        }

        // Преобразует массив категорий из тела запроса в словарь.
        const dictCategoriesFromClient = Object.fromEntries(body.map(c => [c.idLocal, c]))

        // Возвращает категории с сервера, которые либо отсутствуют на клиенте, либо имеют более раннюю дату обновления на сервере.
        return categoriesFromDb
            .filter(category => dictCategoriesFromClient[category.idLocal] ?
                dictCategoriesFromClient[category.idLocal].updatedAtLocal < category.updatedAtLocal : true)
    },

    //default
    async create(ctx) {
        const user = ctx.state.user

        const { body } = ctx.request
        const category = body.data
        category.userId = user.id

        await strapi.entityService.create('api::category.category', {
            data: category
        })
        return category
    }
  }));
