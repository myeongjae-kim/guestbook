import { combineReducers } from "redux";

import { fork } from "redux-saga/effects";
import * as addFormModule from "./add-form";
import * as deleteButtonModule from "./delete-button";
import * as editFormModule from "./edit-form";
import * as rowsModule from "./rows";

export interface IState {
  addForm: addFormModule.State
  deleteButton: deleteButtonModule.State
  editForm: editFormModule.State
  rows: rowsModule.State
}

export const reducer = combineReducers<IState>({
  addForm: addFormModule.reducer,
  deleteButton: deleteButtonModule.reducer,
  editForm: editFormModule.reducer,
  rows: rowsModule.reducer
});

export function* saga() {
  yield fork(addFormModule.saga);
  yield fork(deleteButtonModule.saga);
  yield fork(editFormModule.saga);
  yield fork(rowsModule.saga);
}
