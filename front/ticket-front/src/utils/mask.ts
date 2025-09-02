export const maskCPF = (value: string): string => {
  const cleanedValue = value.replace(/\D/g, '');
  
  if (cleanedValue.length <= 11) {
    return cleanedValue
      .replace(/(\d{3})(\d)/, '$1.$2')
      .replace(/(\d{3})(\d)/, '$1.$2')
      .replace(/(\d{3})(\d{1,2})$/, '$1-$2');
  }
  
  return cleanedValue
    .substring(0, 11)
    .replace(/(\d{3})(\d)/, '$1.$2')
    .replace(/(\d{3})(\d)/, '$1.$2')
    .replace(/(\d{3})(\d{1,2})$/, '$1-$2');
};

export const unmaskCPF = (value: string): string => {
  return value.replace(/\D/g, '');
};