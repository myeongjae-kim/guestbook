import { combineReducers } from "redux";
import { fork } from "redux-saga/effects";
import * as comments from "./comments"
import * as mentions from "./mentions"

export interface IRootState {
  mentions: mentions.IState
  comments: comments.IState
}

export const rootReducer = combineReducers<IRootState>({
  mentions: mentions.reducer,
  comments: comments.reducer
});


export function* rootSaga() {
  yield fork(mentions.saga);
  yield fork(comments.saga);
}
