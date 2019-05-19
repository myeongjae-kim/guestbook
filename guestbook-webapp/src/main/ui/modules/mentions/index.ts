import { combineReducers } from "redux";

import * as tableModule from "./table";

export interface IState {
  table: tableModule.State;
}

export const reducer = combineReducers<IState>({
  table: tableModule.reducer,
});
