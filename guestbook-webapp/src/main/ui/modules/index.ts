import { combineReducers } from "redux";
import * as mentions from "./mentions"

export interface IRootState {
  mentions: mentions.IState
}

export const rootReducer = combineReducers<IRootState>({
  mentions: mentions.reducer
});
