import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

import { Button } from '@mui/material';

import { getClients } from 'services/api/users';
import history from 'utils/history';
import Title from '../Title';
import ClientsTable from '../tables/ClientsTable';

const NO_OF_CLIENTS = 5;

const ClientsList = props => {
  const { title } = props;

  const [clients, setClients] = useState([]);

  useEffect(() => {
    const getRecentClients = async () => {
      const response = await getClients(null, NO_OF_CLIENTS).catch(err =>
        console.log(err.response)
      );
      if (response) {
        setClients(response.data);
      }
    };
    getRecentClients();
  }, []);

  const handleSelectClient = client => {
    history.push(`/admin/clients/update/${client.id}`);
  };

  return (
    <React.Fragment>
      <Title>{title}</Title>
      <ClientsTable clients={clients} onSelectClient={handleSelectClient} />
      <Link
        className="link"
        to="/admin/clients"
        style={{ marginTop: '1rem', width: '100%' }}
      >
        <Button color="primary" variant="outlined" size="small" fullWidth>
          View All
        </Button>
      </Link>
    </React.Fragment>
  );
};

export default ClientsList;
