import * as React from 'react';
import { Table } from 'semantic-ui-react';

const MentionTableHeader: React.FC = () =>
  <Table.Header>
    <Table.Row>
      <Table.HeaderCell>ID</Table.HeaderCell>
      <Table.HeaderCell>이름</Table.HeaderCell>
      <Table.HeaderCell>내용</Table.HeaderCell>
      <Table.HeaderCell>작성일시</Table.HeaderCell>
    </Table.Row>
  </Table.Header>

export default MentionTableHeader;