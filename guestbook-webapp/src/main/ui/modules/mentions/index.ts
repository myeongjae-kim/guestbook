import { combineReducers } from "redux";

import * as addFormModule from "./add-form";
import * as deleteButtonModule from "./delete-button";
import * as tableModule from "./table";

export interface IState {
  deleteButton: deleteButtonModule.State
  addForm: addFormModule.State
  table: tableModule.State
}

export const reducer = combineReducers<IState>({
  deleteButton: deleteButtonModule.reducer,
  addForm: addFormModule.reducer,
  table: tableModule.reducer
});
