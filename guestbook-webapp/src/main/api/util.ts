import { format } from "date-fns";
import * as ko from 'date-fns/locale/ko';

export const toDateString = (createdAt: string) => format(createdAt, "YYYY. MM. DD. (ddd)", { locale: ko })