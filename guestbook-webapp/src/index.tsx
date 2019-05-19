import Home from 'main/ui/component/templates/Home';
import { rootReducer } from 'main/ui/modules';
import refreshTableAfterMentionCRUD from 'main/ui/util';
import React from 'react';
import ReactDOM from 'react-dom';
import withStyles from 'react-jss';
import { Provider as ReduxStoreProvider } from "react-redux";
import { applyMiddleware, createStore } from 'redux';
import { createPromise } from 'redux-promise-middleware';
import 'semantic-ui-css/semantic.min.css'
import * as serviceWorker from './serviceWorker';

const styles = {
  '@global': {
    body: {
      margin: 0,
      padding: 0,
      fontFamily: `'BM HANNA Air', -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen',
    'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue',
    sans-serif`,
      "-webkit-font-smoothing": "antialiased",
      "-moz-osx-font-smoothing": "grayscale",
      backgroundColor: "#fafafa"
    },

    code: {
      fontFamily: `source-code-pro, Menlo, Monaco, Consolas, 'Courier New',
    monospace`
    },

    "::-moz-selection": {
      backgroundColor: "#2ac1bc",
      color: "#fff"
    },
    "::selection": {
      backgroundColor: "#2ac1bc",
      color: "#fff"
    }
  }
}

const store = (() => {
  const promiseMiddleware = createPromise();
  if (process.env.NODE_ENV === "development") {
    const { composeWithDevTools } = require('redux-devtools-extension');
    const { createLogger } = require('redux-logger');

    const logger = createLogger();
    return createStore(rootReducer, composeWithDevTools(applyMiddleware(
      logger,
      promiseMiddleware,
      refreshTableAfterMentionCRUD)));
  } else {
    return createStore(rootReducer, applyMiddleware(
      promiseMiddleware,
      refreshTableAfterMentionCRUD));
  }
})();


const App = withStyles(styles)(() =>
  <ReduxStoreProvider store={store}>
    <Home />
  </ReduxStoreProvider>)

ReactDOM.render(<App />, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
