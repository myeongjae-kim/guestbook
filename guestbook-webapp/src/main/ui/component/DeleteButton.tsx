import * as React from 'react';
import { Button } from 'semantic-ui-react';

interface IProps {
  del(): void
}

const DeleteButton: React.FC<IProps> = ({ del }) => {
  return <Button onClick={del} icon="delete" />;
}

export default DeleteButton;