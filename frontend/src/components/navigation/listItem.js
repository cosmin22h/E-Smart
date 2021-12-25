import React from 'react';
import { Link } from 'react-router-dom';

import {
  ListItem,
  ListItemIcon,
  ListItemText,
  ListSubheader,
} from '@mui/material';

import {
  mainListAdmin,
  secondListAdmin,
  mainListClient,
  secondListClient,
} from './lists';

const renderListItems = list => {
  return list.map((item, index) => (
    <Link key={index} className="link" to={item.link}>
      <ListItem button>
        <ListItemIcon>{item.icon}</ListItemIcon>
        <ListItemText primary={`${item.text}`} />
      </ListItem>
    </Link>
  ));
};

export const mainListItemsAdmin = (
  <React.Fragment>{renderListItems(mainListAdmin)}</React.Fragment>
);

export const secondListItemsAdmin = (
  <React.Fragment>{renderListItems(secondListAdmin)}</React.Fragment>
);

export const mainListItemsClient = (
  <React.Fragment>{renderListItems(mainListClient)}</React.Fragment>
);

export const secondaryListItemsClient = (
  <React.Fragment>
    <ListSubheader inset>Reports</ListSubheader>
    {renderListItems(secondListClient)}
  </React.Fragment>
);
