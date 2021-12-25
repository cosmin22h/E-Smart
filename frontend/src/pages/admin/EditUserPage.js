import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { connect } from 'react-redux';

import { Box } from '@mui/material';

import { unselectClient } from 'store/actions';
import { updateClient, getClient } from 'services/api/users';
import history from 'utils/history';

import ClientForm from 'components/forms/ClientForm';

const EditUserPage = props => {
  const { selectedClient, unselectClient } = props;
  const { id } = useParams();
  const [client, setClient] = useState(null);
  const [address, setAddress] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const getClientById = async () => {
      const response = await getClient(id).catch(err => {
        setError(err.response.data.message);
      });
      if (response) {
        handleSelectedClient(response.data);
      }
    };
    if (selectedClient.id) {
      handleSelectedClient(selectedClient);
    } else {
      getClientById();
    }

    return () => {
      unselectClient();
    };
  }, [selectedClient, id, unselectClient]);

  const handleSelectedClient = selectedClient => {
    setClient({
      id: selectedClient.id,
      username: selectedClient.username,
      email: selectedClient.email,
      role: 'CLIENT',
      firstName: selectedClient.firstName,
      lastName: selectedClient.lastName,
      birthday: selectedClient.birthday,
    });
    setAddress(selectedClient.address);
  };

  const handleOnSubmit = async clientBody => {
    const response = await updateClient(clientBody).catch(err => {
      setError(err.response.data.message);
    });
    if (response) {
      history.push('/admin/clients');
    }
  };

  const handleCloseAlertError = () => {
    setError(null);
  };

  if (!client || !address) {
    return null;
  }

  return (
    <Box>
      <ClientForm
        isEditMode={true}
        titleForm="Edit client"
        defaultClient={client}
        defaultAddress={address}
        onSubmit={handleOnSubmit}
        buttonName="Update"
        error={error}
        onCloseAlert={handleCloseAlertError}
      />
    </Box>
  );
};

const mapStateToProps = state => {
  return {
    selectedClient: state.selectedClient,
  };
};

export default connect(mapStateToProps, { unselectClient })(EditUserPage);
