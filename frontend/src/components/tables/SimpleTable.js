import React from 'react';

import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
} from '@mui/material';

const SimpleTable = props => {
  const { header, body } = props;

  const renderTableHeaderCells = headerCells =>
    headerCells.map((cell, index) => <TableCell key={index}>{cell}</TableCell>);

  return (
    <React.Fragment>
      <Table size="small">
        <TableHead>
          <TableRow>{renderTableHeaderCells(header)}</TableRow>
        </TableHead>
        <TableBody>{body}</TableBody>
      </Table>
    </React.Fragment>
  );
};

export default SimpleTable;
