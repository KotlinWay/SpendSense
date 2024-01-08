import type { Schema, Attribute } from '@strapi/strapi';

export interface TimestampsDatetime extends Schema.Component {
  collectionName: 'components_timestamps_datetimes';
  info: {
    displayName: 'Datetime';
    icon: 'alien';
  };
  attributes: {
    createdAtLocal: Attribute.DateTime;
    updatedAtLocal: Attribute.DateTime;
  };
}

declare module '@strapi/types' {
  export module Shared {
    export interface Components {
      'timestamps.datetime': TimestampsDatetime;
    }
  }
}
