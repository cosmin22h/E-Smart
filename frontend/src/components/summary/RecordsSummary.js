import React from 'react';

import { TableCell, TableRow } from '@mui/material';

import SimpleTable from 'components/tables/SimpleTable';

const header = ['Time', 'Value'];

const RecordsSummary = props => {
  const { records } = props;

  const renderBody = () =>
    records.map(record => (
      <TableRow key={record.id}>
        <TableCell>{record.timestamp}</TableCell>
        <TableCell>{record.energyConsumption}</TableCell>
      </TableRow>
    ));

  return <SimpleTable header={header} body={renderBody()} />;
};

export default RecordsSummary;
