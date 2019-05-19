import { combineReducers } from "redux";

import * as addFormModule from "./add-form";
import * as deleteButtonModule from "./delete-button";
import * as editFormModule from "./edit-form";
import * as tableModule from "./table";

export interface IState {
  deleteButton: deleteButtonModule.State
  addForm: addFormModule.State
  editForm: editFormModule.State
  table: tableModule.State
}

export const reducer = combineReducers<IState>({
  deleteButton: deleteButtonModule.reducer,
  addForm: addFormModule.reducer,
  editForm: editFormModule.reducer,
  table: tableModule.reducer
});
