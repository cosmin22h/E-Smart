import React from 'react';

import { CssBaseline } from '@mui/material';
import {
  createTheme,
  responsiveFontSizes,
  ThemeProvider,
} from '@mui/material/styles';

import {
  BACKGROUND,
  ERROR,
  INFO,
  PRIMARY,
  SECONDARY,
  SUCCESS,
  WARNING,
} from './colors';

const bodyStyle = {
  height: '100vh',
  backgroundColor: BACKGROUND,
};

const Theme = ({ children }) => {
  let themeConfig = createTheme({
    palette: {
      primary: PRIMARY,
      secondary: SECONDARY,
      error: ERROR,
      warning: WARNING,
      info: INFO,
      success: SUCCESS,
    },
  });
  themeConfig = responsiveFontSizes(themeConfig);

  return (
    <ThemeProvider theme={themeConfig}>
      <div style={{ ...bodyStyle }}>
        <CssBaseline />
        {children}
      </div>
    </ThemeProvider>
  );
};

export default Theme;
