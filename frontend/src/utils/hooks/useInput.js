import { useState } from 'react';

const useInput = initialValue => {
  const [value, setValue] = useState(initialValue);

  const onChange = event => {
    const target = event.target;
    const name = target.name;
    const value = target.value;

    setValue(currentValue => ({ ...currentValue, [name]: value }));
  };

  return [value, onChange];
};

export default useInput;
