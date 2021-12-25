import React from 'react';
import { connect } from 'react-redux';

import LoadingSpinner from 'components/LoadingSpinner';

const BASE_ADMIN = '/admin';
const BASE_CLIENT = '/client';

const requireNoAuth = ChildComponent => {
  class ComposedComponent extends React.Component {
    state = {
      isLoading: true,
    };

    componentDidMount() {
      this.shouldNavigateAway();
    }

    shouldNavigateAway() {
      const { history, auth } = this.props;
      const path = history.location.pathname;
      if (auth.role) {
        if (path.search(BASE_ADMIN)) {
          history.push(BASE_ADMIN);
        } else if (path.search(BASE_CLIENT)) {
          history.push(BASE_CLIENT);
        }
      }
      this.setState({ isLoading: false });
    }
    render() {
      if (this.state.isLoading) {
        return <LoadingSpinner />;
      }
      return <ChildComponent {...this.props} />;
    }
  }

  const mapStateToProps = state => {
    return {
      auth: state.auth,
    };
  };

  return connect(mapStateToProps)(ComposedComponent);
};

export default requireNoAuth;
