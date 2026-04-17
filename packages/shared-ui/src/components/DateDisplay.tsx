import React from 'react';
import moment from 'moment';

interface DateDisplayProps {
  date: string | Date;
  format?: string;
  relative?: boolean;
}

export const DateDisplay: React.FC<DateDisplayProps> = ({
  date,
  format = 'MMMM D, YYYY',
  relative = false,
}) => {
  const m = moment(date);
  const display = relative ? m.fromNow() : m.format(format);
  const isoString = m.toISOString();

  return <time dateTime={isoString}>{display}</time>;
};
