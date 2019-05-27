import { combineReducers } from "redux";

import { fork } from "redux-saga/effects";
import * as addFormModule from "./add-form";
import * as deleteButtonModule from "./delete-button";
import * as editFormModule from "./edit-form";
import * as tableModule from "./table";

export interface IState {
  addForm: addFormModule.State
  deleteButton: deleteButtonModule.State
  editForm: editFormModule.State
  table: tableModule.State
}

export const reducer = combineReducers<IState>({
  addForm: addFormModule.reducer,
  deleteButton: deleteButtonModule.reducer,
  editForm: editFormModule.reducer,
  table: tableModule.reducer
});

export function* saga() {
  yield fork(addFormModule.saga);
  yield fork(deleteButtonModule.saga);
  yield fork(editFormModule.saga);
  yield fork(tableModule.saga);
}
