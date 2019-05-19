import { Middleware } from "redux";
import { getType } from "typesafe-actions";
import { isUndefined } from "util";
import { postMentionFulfilled } from "./modules/mentions/add-form";
import { deleteMentionFulfilled } from "./modules/mentions/delete-button";
import { putMentionFulfilled } from "./modules/mentions/edit-form";
import { getMentionList } from "./modules/mentions/table";

const refreshTableAfterMentionCRUD: Middleware = api => next => action => {
  const type = action.type as string | undefined
  if (isUndefined(type)) {
    return next(action);
  }

  if (
    type === getType(deleteMentionFulfilled) ||
    type === getType(postMentionFulfilled) ||
    type === getType(putMentionFulfilled)
  ) {
    api.dispatch(getMentionList())
  }

  return next(action);
};

export default refreshTableAfterMentionCRUD 