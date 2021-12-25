import React from 'react';

import { TableCell, TableRow } from '@mui/material';

import { PRIMARY } from 'theme/colors';
import { renderActiveIcon, renderInactiveIcon } from 'utils/renderFunctions';
import SimpleTable from './SimpleTable';

const header = [
  'No.',
  'Description',
  'Location',
  'Average Energy Consumption',
  'Client',
  'Active',
];

const DevicesTable = props => {
  const { devices, selectedDevice, onSelectDevice } = props;

  const renderBodyTable = () =>
    devices.map((device, index) => (
      <TableRow
        key={device.id}
        hover
        onClick={() => onSelectDevice(device)}
        style={{
          backgroundColor:
            selectedDevice && device.id === selectedDevice.id
              ? PRIMARY.light
              : null,
          cursor: 'pointer',
        }}
      >
        <TableCell>{index + 1}</TableCell>
        <TableCell>{device.description}</TableCell>
        <TableCell>{device.location}</TableCell>
        <TableCell>{device.averageEnergyConsumption.toFixed(2)}</TableCell>
        <TableCell>{device.client.username}</TableCell>
        <TableCell>
          {device.disabled ? renderInactiveIcon : renderActiveIcon}
        </TableCell>
      </TableRow>
    ));

  return <SimpleTable header={header} body={renderBodyTable()} />;
};

DevicesTable.defaultProps = {
  selectedDevice: {
    id: null,
  },
  onSelectDevice: () => {},
};

export default DevicesTable;
