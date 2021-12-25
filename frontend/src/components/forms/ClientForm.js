import React, { useState } from 'react';

import {
  Box,
  Paper,
  TextField,
  FormControl,
  InputLabel,
  OutlinedInput,
  IconButton,
  InputAdornment,
  Button,
  MenuItem,
  Menu,
} from '@mui/material';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import MoreIcon from '@mui/icons-material/More';

import { deleteUser } from 'services/api/users';
import useInput from 'utils/hooks/useInput';
import history from 'utils/history';
import Title from 'components/Title';
import ClosingAlert from 'components/alerts/ClosingAlert';
import ConfirmationModal from 'components/modals/ConfirmationModal';

const ROLE = 'CLIENT';

const ClientForm = props => {
  const {
    isEditMode,
    titleForm,
    defaultClient,
    defaultAddress,
    onSubmit,
    buttonName,
    error,
    onCloseAlert,
  } = props;

  const [client, onClientChange] = useInput(defaultClient);
  const [address, onAddressChange] = useInput(defaultAddress);
  const [showPassword, setShowPassword] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleClickShowPassword = () => {
    setShowPassword(!showPassword);
  };

  const handleOpenMenu = e => {
    setAnchorEl(e.currentTarget);
  };

  const handleCloseMenu = () => {
    setAnchorEl(null);
  };

  const handleOpenModal = () => {
    setIsModalOpen(true);
  };

  const handleOnSubmit = event => {
    event.preventDefault();
    const clientBody = { ...client, address };
    onSubmit(clientBody);
  };

  const handleDeleteClient = async () => {
    const response = await deleteUser(client.id).catch(err =>
      console.log(err.response)
    );
    if (response) {
      history.goBack();
    }
  };

  const renderEditButtons = () => (
    <div>
      <IconButton color="primary" onClick={handleOpenMenu}>
        <MoreIcon />
      </IconButton>
      <Menu anchorEl={anchorEl} open={!!anchorEl} onClose={handleCloseMenu}>
        <MenuItem
          onClick={() => history.push(`/admin/clients/devices/${client.id}`)}
        >
          View devices
        </MenuItem>
        <MenuItem
          onClick={() => history.push(`/admin/change-password/${client.id}`)}
        >
          Change password
        </MenuItem>
        <MenuItem onClick={handleOpenModal}>Delete client</MenuItem>
      </Menu>
    </div>
  );

  return (
    <React.Fragment>
      <Paper sx={{ p: 2 }}>
        <div
          style={{
            display: 'flex',
            flexDirection: 'row',
            justifyContent: 'space-between',
          }}
        >
          <div style={{ marginRight: '1rem' }}>
            <Title>{titleForm}</Title>
          </div>
          {isEditMode ? renderEditButtons() : null}
        </div>
        {error ? (
          <ClosingAlert
            severity="error"
            message={error}
            onClose={onCloseAlert}
          />
        ) : null}
        <Box component="form" sx={{ mt: 2 }} onSubmit={handleOnSubmit}>
          <TextField
            id="username"
            name="username"
            type="text"
            label="Username"
            value={client.username}
            required
            fullWidth
            onChange={onClientChange}
          />
          <TextField
            id="email"
            name="email"
            type="email"
            label="Email"
            value={client.email}
            required
            fullWidth
            onChange={onClientChange}
            sx={{ mt: 2 }}
          />
          {!isEditMode ? (
            <FormControl fullWidth required variant="outlined" sx={{ mt: 1 }}>
              <InputLabel htmlFor="password">Password</InputLabel>
              <OutlinedInput
                id="password"
                name="password"
                type={showPassword ? 'text' : 'password'}
                value={client.password}
                label="Password"
                endAdornment={
                  <InputAdornment position="end">
                    <IconButton
                      aria-label="toggle password visibility"
                      onClick={handleClickShowPassword}
                      edge="end"
                      color="primary"
                    >
                      {showPassword ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                }
                onChange={onClientChange}
              />
            </FormControl>
          ) : null}
          <div
            style={{
              marginTop: '1rem',
              display: 'flex',
              justifyContent: 'space-between',
            }}
          >
            <TextField
              id="firstName"
              name="firstName"
              type="text"
              label="First Name"
              value={client.firstName}
              required
              fullWidth
              onChange={onClientChange}
              sx={{ mr: 1 }}
            />
            <TextField
              id="lastName"
              name="lastName"
              type="text"
              label="Last Name"
              value={client.lastName}
              required
              fullWidth
              onChange={onClientChange}
            />
          </div>
          <TextField
            id="birthday"
            name="birthday"
            type="date"
            label="Birthday"
            value={client.birthday}
            required
            fullWidth
            focused
            onChange={onClientChange}
            sx={{ mt: 2 }}
          />
          <div
            style={{
              marginTop: '1rem',
              display: 'flex',
              justifyContent: 'space-between',
            }}
          >
            <TextField
              id="country"
              name="country"
              type="text"
              label="Country"
              value={address.country}
              required
              fullWidth
              onChange={onAddressChange}
              sx={{ mr: 1 }}
            />
            <TextField
              id="region"
              name="region"
              type="text"
              label="Region"
              value={address.region}
              required
              fullWidth
              onChange={onAddressChange}
            />
          </div>
          <TextField
            id="addressOne"
            name="addressOne"
            type="text"
            label="Address"
            value={address.addressOne}
            required
            fullWidth
            onChange={onAddressChange}
            sx={{ mt: 2 }}
          />
          <Button type="submit" fullWidth variant="contained" sx={{ mt: 2 }}>
            {buttonName}
          </Button>
        </Box>
      </Paper>
      {isModalOpen ? (
        <ConfirmationModal
          title="Are you sure you want to delete this user?"
          bodyText={`User: ${client.username}`}
          btnTextConfirm="Delete"
          open={isModalOpen}
          onConfirm={handleDeleteClient}
          onClose={() => setIsModalOpen(false)}
        />
      ) : null}
    </React.Fragment>
  );
};

ClientForm.defaultProps = {
  isEditMode: false,
  defaultClient: {
    username: '',
    email: '',
    password: '',
    role: ROLE,
    firstName: '',
    lastName: '',
    birthday: '',
  },
  defaultAddress: {
    country: '',
    region: '',
    addressOne: '',
  },
};

export default ClientForm;
