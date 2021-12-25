import React, { useState } from 'react';

import { Box } from '@mui/material';

import { register } from 'services/api/auth';
import history from 'utils/history';

import ClientForm from 'components/forms/ClientForm';

const CreateUserPage = props => {
  const [error, setError] = useState(null);

  const handleOnSubmit = async userBody => {
    const response = await register(userBody).catch(err =>
      setError(err.response.data.message)
    );
    if (response) {
      history.push('/admin/clients');
    }
  };

  const handleCloseAlertError = () => {
    setError(null);
  };

  return (
    <Box>
      <ClientForm
        titleForm="Add new client"
        onSubmit={handleOnSubmit}
        buttonName="Add"
        error={error}
        onCloseAlert={handleCloseAlertError}
      />
    </Box>
  );
};

export default CreateUserPage;
