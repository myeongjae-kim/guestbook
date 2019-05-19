import IMentionResponse from 'main/api/mentions/dto/IMentionResponse';
import * as React from 'react';
import { Table } from 'semantic-ui-react';
import MentionTableRow from './row/MentionTableRow';

interface IProps {
  mentions: IMentionResponse[]
}

const MentionTableBody: React.FC<IProps> = ({ mentions }) =>
  <Table.Body>
    {mentions.map((mention, index) => <MentionTableRow mention={mention} key={index} />)}
  </Table.Body>

export default MentionTableBody;