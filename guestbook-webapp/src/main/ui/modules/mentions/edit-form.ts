import { Record } from "immutable";
import ApiError from "main/api/ApiError";
import * as mentions from "main/api/mentions";
import IMentionRequest from "main/api/mentions/dto/IMentionRequest";
import { call, put, takeEvery } from "redux-saga/effects";
import { ActionType, createAction, getType } from "typesafe-actions";
import { getMentionList } from "./table";

export type State = Record<{
  pending: boolean;
  rejected: boolean;
}>

export const putMention = createAction("@mentionEditForm/PUT_MENTION",
  action => (id: number, mentionRequest: IMentionRequest) => action({ id, mentionRequest }));
const putMentionPending = createAction("@mentionEditForm/PUT_MENTION_PENDING");
const putMentionFulfilled = createAction("@mentionEditForm/PUT_MENTION_FULFILLED");
const putMentionRejected = createAction("@mentionEditForm/PUT_MENTION_REJECTED",
  action => (error: ApiError | Error) => action(error))

export type Action = ActionType<
  typeof putMention |
  typeof putMentionPending |
  typeof putMentionFulfilled |
  typeof putMentionRejected
>

const createInitialState = Record({
  pending: false,
  rejected: false
});

export const reducer = (
  state: State = createInitialState(),
  action: Action
): State => {
  switch (action.type) {
    case getType(putMentionPending):
      return state.set("pending", true);
    case getType(putMentionFulfilled):
      alert("글을 수정했습니다.")
      return createInitialState();
    case getType(putMentionRejected):
      alert(action.payload.toString());
      return state.merge({
        pending: false,
        rejected: true
      });

    default:
      return state.merge({});
  }
}

export function* saga() {
  yield takeEvery(getType(putMention), sagaPutMention);
}

function* sagaPutMention(action: ActionType<typeof putMention>) {
  yield put(putMentionPending())
  try {
    const { id, mentionRequest } = action.payload
    yield call(mentions.put, id, mentionRequest);
    yield put(putMentionFulfilled());
    yield put(getMentionList());
  } catch (e) {
    yield put(putMentionRejected(e));
  }
}