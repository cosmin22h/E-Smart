import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';

import { Box, Paper } from '@mui/material';

import { selectClient, unselectClient } from 'store/actions';
import { deleteUser, getClients } from 'services/api/users';

import history from 'utils/history';
import ClosingAlert from 'components/alerts/ClosingAlert';
import FooterTable from 'components/tables/FooterTable';
import ConfirmationModal from 'components/modals/ConfirmationModal';
import ClientsTable from 'components/tables/ClientsTable';
import Pagination from 'components/Pagination';

const ERR_MESSAGE_FETCH = 'Unable to fetch the clients from db!';
const ERR_MESSAGE_DELETE_BASE = 'Unable to delete this client: ';
const ITEMS_PER_PAGE = 5;

const AdminClientsListPage = props => {
  const { client, selectClient, unselectClient } = props;
  const [clients, setClients] = useState([]);
  const [isNextPage, setIsNextPage] = useState(false);
  const [selectedClient, setSelectedClient] = useState(client);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(1);
  const [isDeleteOpen, setIsDeleteOpen] = useState(false);
  const [refresh, setRefresh] = useState(false);

  useEffect(() => {
    const handleGetClients = async () => {
      const responseClients = await getClients(page, ITEMS_PER_PAGE).catch(
        err => handleGetClientsError(err)
      );
      const responseNextClients = await getClients(
        page + 1,
        ITEMS_PER_PAGE
      ).catch(err => handleGetClientsError(err));
      if (responseClients) {
        setClients(responseClients.data);
      }
      if (responseNextClients) {
        setIsNextPage(responseNextClients.data.length > 0);
      }
    };
    handleGetClients();
  }, [page, refresh]);

  useEffect(() => {
    setSelectedClient(client);
  }, [client]);

  const handleGetClientsError = err => {
    setError(ERR_MESSAGE_FETCH);
  };

  const handleNextPageChange = () => {
    setPage(page + 1);
    handleCancelSelectedClient();
  };

  const handlePrevPageChange = () => {
    setPage(page - 1);
    handleCancelSelectedClient();
  };

  const handleSelectClient = client => {
    selectClient(client);
  };

  const handleCancelSelectedClient = () => {
    unselectClient();
  };

  const handleAddClient = () => {
    history.push('/admin/clients/add');
  };

  const handleEditClient = () => {
    history.push(`/admin/clients/update/${selectedClient.id}`);
  };

  const handleDeleteClient = async () => {
    const response = await deleteUser(selectedClient.id).catch(err =>
      handleDeleteClientError(err)
    );
    if (response) {
      setRefresh(!refresh);
      setIsDeleteOpen(false);
      unselectClient();
    }
  };

  const handleDeleteClientError = err => {
    setError(`${ERR_MESSAGE_DELETE_BASE} ${selectedClient.username}!`);
  };

  const handleOpenModalDeleteClient = () => {
    setIsDeleteOpen(true);
  };

  const handleCloseModalDeleteClient = () => {
    setIsDeleteOpen(false);
  };

  const handleCloseError = () => {
    setError(null);
  };

  if (error) {
    return (
      <ClosingAlert
        severity="warning"
        message={error}
        onClose={handleCloseError}
      />
    );
  }

  return (
    <React.Fragment>
      <Box>
        <Paper sx={{ p: 2 }}>
          <ClientsTable
            clients={clients}
            selectedClient={selectedClient}
            onSelectClient={handleSelectClient}
          />
          <FooterTable
            selectedItem={selectedClient}
            onAddItem={handleAddClient}
            onCancelSelectItem={handleCancelSelectedClient}
            onDeleteItem={handleOpenModalDeleteClient}
            onEditItem={handleEditClient}
          />
        </Paper>
        <Pagination
          currentPage={page}
          isNextPage={isNextPage}
          onPrevPage={handlePrevPageChange}
          onNextPage={handleNextPageChange}
        />
      </Box>
      {selectedClient ? (
        <ConfirmationModal
          title="Are you sure you want to delete this user?"
          bodyText={`User: ${selectedClient.username}`}
          btnTextConfirm="Delete"
          open={isDeleteOpen}
          onConfirm={handleDeleteClient}
          onClose={handleCloseModalDeleteClient}
        />
      ) : null}
    </React.Fragment>
  );
};

const mapStateToProps = state => {
  return {
    client: state.selectedClient,
  };
};

export default connect(mapStateToProps, { selectClient, unselectClient })(
  AdminClientsListPage
);
