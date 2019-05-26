import createSagaMiddleware from "@redux-saga/core";
import Home from 'main/ui/component/templates/Home';
import { IRootState, rootReducer, rootSaga } from 'main/ui/modules';
import React from 'react';
import ReactDOM from 'react-dom';
import withStyles from 'react-jss';
import { Provider as ReduxStoreProvider } from "react-redux";
import { AnyAction, applyMiddleware, createStore, Store } from 'redux';
import 'semantic-ui-css/semantic.min.css';
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
  const sagaMiddleware = createSagaMiddleware();

  let reduxStore: Store<IRootState, AnyAction>;
  if (process.env.NODE_ENV === "development") {
    const { composeWithDevTools } = require('redux-devtools-extension');
    const { createLogger } = require('redux-logger');

    const logger = createLogger();
    reduxStore = createStore(rootReducer, composeWithDevTools(applyMiddleware(logger, sagaMiddleware)));
  } else {
    reduxStore = createStore(rootReducer, applyMiddleware(sagaMiddleware));
  }

  sagaMiddleware.run(rootSaga);

  return reduxStore;
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
