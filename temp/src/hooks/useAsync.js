import { useCallback, useState } from 'react';

export const useAsync = (asyncFn, deps = []) => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const wrapped = useCallback(
    async (...args) => {
      setLoading(true);
      setError(null);
      try {
        const result = await asyncFn(...args);
        return result;
      } catch (err) {
        setError(err);
        throw err;
      } finally {
        setLoading(false);
      }
    },
    // eslint-disable-next-line react-hooks/exhaustive-deps
    deps,
  );

  return { execute: wrapped, loading, error };
};

