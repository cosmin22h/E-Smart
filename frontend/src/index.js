import React from 'react';
import ReactDOM from 'react-dom';

import Root from 'Root';
import App from 'App';
import Theme from 'theme';

ReactDOM.render(
  <Root>
    <Theme>
      <App />
    </Theme>
  </Root>,
  document.getElementById('root')
);
