import React from 'react';

import { TableCell, TableRow } from '@mui/material';

import SimpleTable from 'components/tables/SimpleTable';

const header = ['Description', 'Max value'];

const SensorSummary = props => {
  const { sensor } = props;

  const renderBody = () => (
    <TableRow>
      <TableCell>{sensor.description}</TableCell>
      <TableCell>{sensor.maxValue}</TableCell>
    </TableRow>
  );

  return <SimpleTable header={header} body={renderBody()} />;
};

export default SensorSummary;
