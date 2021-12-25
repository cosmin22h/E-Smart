import React, { useState } from 'react';
import { connect } from 'react-redux';
import { useParams } from 'react-router-dom';

import {
  Box,
  Paper,
  FormControl,
  InputLabel,
  OutlinedInput,
  IconButton,
  InputAdornment,
  Button,
} from '@mui/material';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';

import { changePassword } from 'services/api/users';
import history from 'utils/history';

import Title from 'components/Title';
import ClosingAlert from 'components/alerts/ClosingAlert';

const ERR_MESSAGE_MAPPING = 'Passwords must be the same!';

const ChangePasswordForm = props => {
  const { role } = props;
  const { id } = useParams();
  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmNewPassword, setConfirmNewPassword] = useState('');
  const [error, setError] = useState(null);
  const [showPassword, setShowPassword] = useState(false);

  const handleClickShowPassword = () => {
    setShowPassword(!showPassword);
  };

  const handleOnSubmit = async e => {
    e.preventDefault();
    if (newPassword !== confirmNewPassword) {
      setError(ERR_MESSAGE_MAPPING);
      return null;
    }
    const response = await changePassword({
      isAdmin: role === 'ADMIN',
      idUserToChange: id,
      oldPassword: oldPassword,
      newPassword: newPassword,
    }).catch(err => {
      setError(err.response.data.message);
    });

    if (response) {
      history.goBack();
    }
  };

  return (
    <Paper sx={{ p: 2 }}>
      <div
        style={{
          display: 'flex',
          flexDirection: 'row',
          justifyContent: 'space-between',
        }}
      >
        <Title>Change password</Title>
        <Button variant="outlined" onClick={() => history.goBack()}>
          Back
        </Button>
      </div>
      {error ? (
        <ClosingAlert
          severity="error"
          message={error}
          onClose={() => setError(null)}
        />
      ) : null}
      <Box component="form" sx={{ mt: 2 }} onSubmit={handleOnSubmit}>
        {role !== 'ADMIN' ? (
          <FormControl fullWidth required variant="outlined" sx={{ mt: 1 }}>
            <InputLabel htmlFor="oldPassword">Old Password</InputLabel>
            <OutlinedInput
              id="oldPassword"
              name="oldPassword"
              type={showPassword ? 'text' : 'password'}
              value={oldPassword}
              label="Old Password"
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
              onChange={e => setOldPassword(e.target.value)}
            />
          </FormControl>
        ) : null}
        <FormControl fullWidth required variant="outlined" sx={{ mt: 1 }}>
          <InputLabel htmlFor="newPassword">New Password</InputLabel>
          <OutlinedInput
            id="newPassword"
            name="newPassword"
            type={showPassword ? 'text' : 'password'}
            value={newPassword}
            label="New Password"
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
            onChange={e => setNewPassword(e.target.value)}
          />
        </FormControl>
        <FormControl fullWidth required variant="outlined" sx={{ mt: 1 }}>
          <InputLabel htmlFor="confirmNewPassword">
            Confirm New Password
          </InputLabel>
          <OutlinedInput
            id="confirmNewPassword"
            name="confirmNewPassword"
            type={showPassword ? 'text' : 'password'}
            value={confirmNewPassword}
            label="Confirm New Password"
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
            onChange={e => setConfirmNewPassword(e.target.value)}
          />
        </FormControl>
        <Button type="submit" fullWidth variant="contained" sx={{ mt: 2 }}>
          Change
        </Button>
      </Box>
    </Paper>
  );
};

const mapStateToProps = state => {
  return {
    role: state.auth.role,
  };
};

export default connect(mapStateToProps)(ChangePasswordForm);
