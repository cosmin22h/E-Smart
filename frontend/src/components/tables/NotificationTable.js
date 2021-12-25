import React from 'react';

import { TableCell, TableRow, Button } from '@mui/material';

import { renderActiveIcon, renderInactiveIcon } from 'utils/renderFunctions';

import SimpleTable from './SimpleTable';

const header = ['Sensor', 'Measured value', 'Timestamp', 'Read', ''];

const NotificationTable = props => {
  const { notifications, onSelectNotification } = props;

  const renderBodyTable = () =>
    notifications.map(notification => (
      <TableRow key={notification.id} hover>
        <TableCell>{notification.device.sensor.description}</TableCell>
        <TableCell>
          {notification.record.energyConsumption.toFixed(2)}
        </TableCell>
        <TableCell>{notification.timestamp}</TableCell>
        <TableCell>
          {notification.isRead ? renderActiveIcon : renderInactiveIcon}
        </TableCell>
        <TableCell>
          <Button
            variant="outlined"
            onClick={() => onSelectNotification(notification)}
          >
            Read
          </Button>
        </TableCell>
      </TableRow>
    ));

  return <SimpleTable header={header} body={renderBodyTable()} />;
};

export default NotificationTable;
