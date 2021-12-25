import './SignInPage.scss';
import React, { useState } from 'react';
import { connect } from 'react-redux';

import {
  Avatar,
  Box,
  Typography,
  TextField,
  FormControl,
  InputLabel,
  OutlinedInput,
  IconButton,
  InputAdornment,
  Button,
} from '@mui/material';
import LockIcon from '@mui/icons-material/Lock';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';

import useInput from 'utils/hooks/useInput';
import history from 'utils/history';
import { login } from 'services/api/auth';
import { signIn } from 'store/actions';

import ClosingAlert from 'components/alerts/ClosingAlert';
import Copyright from 'components/Copyright';
import requireNoAuth from 'components/guards/requireNoAuth';
import LoadingSpinner from 'components/LoadingSpinner';

const SignInPage = props => {
  const [signInForm, onSingInFormChange] = useInput({
    username: '',
    password: '',
  });
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState(null);
  const [waiting, setWaiting] = useState(false);

  const handleClickShowPassword = () => {
    setShowPassword(!showPassword);
  };

  const handleOnSubmit = async event => {
    event.preventDefault();
    setWaiting(true);
    const response = await login(signInForm).catch(err =>
      handleSignInError(err)
    );
    if (response) {
      handleSignInResponse(response.data);
    }
  };

  const handleSignInResponse = data => {
    setWaiting(false);
    const token = JSON.stringify(data);
    props.signIn(data);
    localStorage.setItem('token', token);
    const redirectTo = data.role.toString().toLowerCase();
    history.push(`/${redirectTo}`);
  };

  const handleSignInError = err => {
    setWaiting(false);
    if (err.response) {
      const { data } = err.response;
      setError(data.message);
    }
    console.log(err);
  };

  const handleCloseError = () => {
    setError(null);
  };

  if (waiting) {
    return <LoadingSpinner />;
  }

  return (
    <div className="login-page">
      <Box className="login-box">
        <Avatar className="logo">
          <LockIcon />
        </Avatar>
        <Typography component="h1" variant="h5" sx={{ mt: 0.5 }}>
          Sign In
        </Typography>
        {error ? (
          <ClosingAlert
            severity="error"
            message={error}
            onClose={handleCloseError}
          />
        ) : null}

        <Box component="form" sx={{ mt: 1 }} onSubmit={handleOnSubmit}>
          <TextField
            id="username"
            name="username"
            type="text"
            value={signInForm.username}
            label="Username"
            required
            fullWidth
            autoComplete="username"
            autoFocus
            margin="normal"
            onChange={onSingInFormChange}
          />
          <FormControl fullWidth required variant="outlined">
            <InputLabel htmlFor="password">Password</InputLabel>
            <OutlinedInput
              id="password"
              name="password"
              type={showPassword ? 'text' : 'password'}
              value={signInForm.password}
              label="Password"
              autoComplete="current-password"
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
              onChange={onSingInFormChange}
            />
          </FormControl>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Sign In
          </Button>
        </Box>
        <Copyright sx={{ m: 1 }} />
      </Box>
    </div>
  );
};

export default connect(null, { signIn })(requireNoAuth(SignInPage));
