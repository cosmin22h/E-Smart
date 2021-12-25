import React from 'react';

import { TableCell, TableRow } from '@mui/material';

import SimpleTable from './SimpleTable';

const header = ['ID', 'Device Description', 'Client'];

const SensorsDetailsTable = props => {
  const { sensors } = props;

  const renderBodyTable = () =>
    sensors.map(sensor => (
      <TableRow key={sensor.id} hover>
        <TableCell>{sensor.id}</TableCell>
        <TableCell>{sensor.deviceDescription}</TableCell>
        <TableCell>{sensor.clientUsername}</TableCell>
      </TableRow>
    ));

  return <SimpleTable header={header} body={renderBodyTable()} />;
};

export default SensorsDetailsTable;
