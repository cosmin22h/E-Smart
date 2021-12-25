import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';

import { Box } from '@mui/material';

import { getAdmin, updateAdmin } from 'services/api/users';
import AdminForm from 'components/forms/AdminForm';

const ERR_MESSAGE_FETCH = 'Unable to fetch your info from db!';

const AdminProfilePage = props => {
  const { id } = props;
  const [admin, setAdmin] = useState(null);
  const [error, setError] = useState(null);
  const [refresh, setRefresh] = useState(false);

  useEffect(() => {
    const handleGetAdmin = async () => {
      const response = await getAdmin(id).catch(err =>
        setError(ERR_MESSAGE_FETCH)
      );
      if (response) {
        setAdmin(response.data);
      }
    };
    handleGetAdmin();
  }, [id, refresh]);

  const handleOnSubmit = async adminBody => {
    const response = updateAdmin(adminBody).catch(err =>
      setError(err.response.data.message)
    );
    if (response) {
      setRefresh(!refresh);
    }
  };

  const handleCloseAlertError = () => {
    setError(null);
  };

  if (!admin) {
    return null;
  }

  return (
    <Box>
      <AdminForm
        defaultAdmin={admin}
        onSubmit={handleOnSubmit}
        error={error}
        onCloseAlert={handleCloseAlertError}
      />
    </Box>
  );
};

const mapStateToProps = state => {
  return {
    id: state.auth.id,
  };
};

export default connect(mapStateToProps)(AdminProfilePage);
