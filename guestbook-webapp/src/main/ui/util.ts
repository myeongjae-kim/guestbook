import { Middleware } from "redux";
import { getType } from "typesafe-actions";
import { isUndefined } from "util";
import { deleteMentionFulfilled } from "./modules/mentions/delete-button";
import { getMentionList } from "./modules/mentions/table";

const refreshTableAfterMentionCRUD: Middleware = api => next => action => {
  const type = action.type as string | undefined
  if (isUndefined(type)) {
    return next(action);
  }

  if (getType(deleteMentionFulfilled) === type) {
    api.dispatch(getMentionList())
  }

  return next(action);
};

export default refreshTableAfterMentionCRUD 