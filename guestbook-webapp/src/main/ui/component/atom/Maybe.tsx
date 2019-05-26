import * as React from 'react';

export default ({ test, children }: React.PropsWithChildren<{ test: any }>) => <>{test && children}</>