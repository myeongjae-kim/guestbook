import { combineReducers } from "redux";
import { fork } from "redux-saga/effects";
import * as mentions from "./mentions"

export interface IRootState {
  mentions: mentions.IState
}

export const rootReducer = combineReducers<IRootState>({
  mentions: mentions.reducer,
});


export function* rootSaga() {
  yield fork(mentions.saga);
}
