import * as React from 'react';
import { Button } from 'semantic-ui-react';

interface IProps {
  deleteMention(): void
}

const MentionDeleteButton: React.FC<IProps> = ({ deleteMention }) => {
  return <Button onClick={deleteMention} icon="delete" />;
}

export default MentionDeleteButton;