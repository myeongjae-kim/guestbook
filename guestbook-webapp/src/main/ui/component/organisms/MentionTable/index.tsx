import IMentionResponse from 'main/api/mentions/dto/IMentionResponse';
import * as React from 'react';
import { Table } from 'semantic-ui-react';
import MentionTableBody from './MentionTableBody';
import MentionTableHeader from './MentionTableHeader';

interface IProps {
  mentions: IMentionResponse[]
}

const MentionTable: React.FC<IProps> = ({ mentions }) =>
  <Table singleLine>
    <MentionTableHeader />
    <MentionTableBody mentions={mentions} />
  </Table>

export default MentionTable;