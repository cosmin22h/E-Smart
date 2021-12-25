import React from 'react';

import { TableCell, TableRow } from '@mui/material';

import { renderActiveIcon, renderInactiveIcon } from 'utils/renderFunctions';

import SimpleTable from 'components/tables/SimpleTable';

const header = [
  'Description',
  'Location',
  'Average Energy Consumption',
  'Max Energy Consumption',
  'Active',
];

const DeviceSummary = props => {
  const { device } = props;

  const renderBody = () => (
    <TableRow>
      <TableCell>{device.description}</TableCell>
      <TableCell>{device.location}</TableCell>
      <TableCell>{device.averageEnergyConsumption.toFixed(2)}</TableCell>
      <TableCell>{device.maxEnergyConsumption}</TableCell>
      <TableCell>
        {device.disabled ? renderInactiveIcon : renderActiveIcon}
      </TableCell>
    </TableRow>
  );

  return <SimpleTable header={header} body={renderBody()} />;
};

export default DeviceSummary;
