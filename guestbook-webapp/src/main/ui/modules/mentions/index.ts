import { combineReducers } from "redux";

import * as deleteButtonModule from "./delete-button";
import * as tableModule from "./table";

export interface IState {
  table: tableModule.State;
  deleteButton: deleteButtonModule.State
}

export const reducer = combineReducers<IState>({
  table: tableModule.reducer,
  deleteButton: deleteButtonModule.reducer
});
