import React from 'react';

import { TableCell, TableRow } from '@mui/material';

import { PRIMARY } from 'theme/colors';
import { renderOnline, renderAddress } from 'utils/renderFunctions';

import SimpleTable from './SimpleTable';

const header = [
  'No.',
  'Username',
  'Email',
  'Address',
  'No. of devices',
  'Last login',
];

const ClientsTable = props => {
  const { clients, selectedClient, onSelectClient } = props;

  const renderBodyTable = () =>
    clients.map((client, index) => (
      <TableRow
        key={client.id}
        hover
        onClick={() => onSelectClient(client)}
        style={{
          backgroundColor:
            selectedClient && client.id === selectedClient.id
              ? PRIMARY.light
              : null,
          cursor: 'pointer',
        }}
      >
        <TableCell>{index + 1}</TableCell>
        <TableCell>{client.username}</TableCell>
        <TableCell>{client.email}</TableCell>
        <TableCell>{renderAddress(client)}</TableCell>
        <TableCell>{client.noOfDevices}</TableCell>
        <TableCell>{renderOnline(client.lastSession)}</TableCell>
      </TableRow>
    ));

  return <SimpleTable header={header} body={renderBodyTable()} />;
};

ClientsTable.defaultProps = {
  selectedClient: {
    id: null,
  },
  onSelectClient: () => {},
};

export default ClientsTable;
